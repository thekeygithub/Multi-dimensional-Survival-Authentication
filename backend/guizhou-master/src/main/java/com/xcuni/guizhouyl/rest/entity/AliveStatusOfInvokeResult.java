package com.xcuni.guizhouyl.rest.entity;

import lombok.Data;

@Data
public class AliveStatusOfInvokeResult {
    private int IsAlive;
    private String SurvivalStatusDate;
    private String CauseOfDeath;
    private String DataSrc;
    private String DataID;
    private String UpdateTime;
}
