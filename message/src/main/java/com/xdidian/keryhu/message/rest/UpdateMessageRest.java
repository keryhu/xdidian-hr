package com.xdidian.keryhu.message.rest;

import com.xdidian.keryhu.domain.message.ReadGroup;
import com.xdidian.keryhu.domain.message.Subject;
import com.xdidian.keryhu.message.domain.SubjectMsg;
import com.xdidian.keryhu.message.domain.UpdateMessageForm;
import com.xdidian.keryhu.message.repository.MessageRepository;
import com.xdidian.keryhu.message.service.MessageService;
import com.xdidian.keryhu.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hushuming on 2016/11/12.
 *
 * 当用户 点击前台的未读  菜单，从而促发 更新后台的未读消息的rest，更新完数据后，
 * 再次返回给前台剩下的未读消息
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class UpdateMessageRest {

    private final MessageRepository repository;
    private final MessageService messageService;

    @PostMapping("/updateMessage")
    public ResponseEntity update(@RequestBody final UpdateMessageForm form){
        // userId 查看userId，是否是当前的user
        String userId= SecurityUtils.getCurrentLogin();

        // 查看是否是新地点的管理员或者客服
        Boolean isXidian = SecurityUtils.isXdidian();

        Set<SubjectMsg> subjectMsgs = new HashSet<>();

        if(isXidian){
            repository.findByReadGroup(ReadGroup.XDIDIAN)
                    .ifPresent(e->{
                        // update subject
                        Set<SubjectMsg> newS=updateSubjectMsg(e.getSubjectMsgs(),form.getSubject());
                        e.setSubjectMsgs(newS);
                        repository.save(e);
                        subjectMsgs.addAll(newS);
                    });

        }

        else {
            repository.findByReadGroupAndUserId(ReadGroup.INDIVIDUAL,userId)
                    .ifPresent(e->{
                        // update subject
                        Set<SubjectMsg> newS=updateSubjectMsg(e.getSubjectMsgs(),form.getSubject());
                        e.setSubjectMsgs(newS);
                        repository.save(e);
                        subjectMsgs.addAll(newS);
                    });
        }

        return ResponseEntity.ok(subjectMsgs);
    }

    // 将指定的subject 从数据库中 本人的名下移除
    private Set<SubjectMsg> updateSubjectMsg(Set<SubjectMsg> subjectMsgs,
                                             Subject subject){
        // update subject
        return subjectMsgs.stream()
                .filter(v->v.getSubject()!=subject)
                .collect(Collectors.toSet());
    }
}
