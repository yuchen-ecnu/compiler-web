package com.ecnu.compiler.rbac.domain.vo;

import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.User;

public class HistoryVO {
    private History history;
    private User user;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
