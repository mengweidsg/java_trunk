package com.esunny.satellite.remote.entity;

import java.math.BigDecimal;

import com.esunny.platform.util.page.Query;

public class DwEsitePvDay extends Query {

    private static final long serialVersionUID = -2730331373018155897L;

    private Long              userId;

    private Long              pv;

    private Long              visit;

    private BigDecimal        lastLogId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
    }

    public BigDecimal getLastLogId() {
        return lastLogId;
    }

    public void setLastLogId(BigDecimal lastLogId) {
        this.lastLogId = lastLogId;
    }
}
