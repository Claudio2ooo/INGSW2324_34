package it.unina.dietideals24.model;

import it.unina.dietideals24.enumerations.StateEnum;

public class Notification {
    private Long id;
    private StateEnum state;

    private DietiUser receiver;
    private EnglishAuction endedEnglishAuction;
    private DownwardAuction endedDownwardAuction;

    public Notification(StateEnum state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public DietiUser getReceiver() {
        return receiver;
    }

    public void setReceiver(DietiUser receiver) {
        this.receiver = receiver;
    }

    public EnglishAuction getEndedEnglishAuction() {
        return endedEnglishAuction;
    }

    public void setEndedEnglishAuction(EnglishAuction endedEnglishAuction) {
        this.endedEnglishAuction = endedEnglishAuction;
    }

    public DownwardAuction getEndedDownwardAuction() {
        return endedDownwardAuction;
    }

    public void setEndedDownwardAuction(DownwardAuction endedDownwardAuction) {
        this.endedDownwardAuction = endedDownwardAuction;
    }
}
