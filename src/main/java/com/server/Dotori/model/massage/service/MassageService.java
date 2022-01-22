package com.server.Dotori.model.massage.service;

import java.time.DayOfWeek;

public interface MassageService {
    void requestMassage(DayOfWeek dayOfWeek, int hour);

}
