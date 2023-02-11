package com.asleepyfish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-09 23:59
 * @Description: AI问答
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ai_qa", schema = "main")
public class AiQa {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "user")
    private String user;

    @Basic
    @Column(name = "question")
    private String question;

    @Basic
    @Column(name = "answer")
    private String answer;

    /**
     * 为0表示成功，-1为失败
     */
    @Basic
    @Column(name = "status")
    private Integer status;
}
