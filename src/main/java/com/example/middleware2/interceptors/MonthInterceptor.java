package com.example.middleware2.interceptors;

import com.example.middleware2.models.Month;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

public class MonthInterceptor implements HandlerInterceptor {

    private List<Month> months = new ArrayList<>();

    public MonthInterceptor() {
        months.add(new Month(1, "January", "Gennaio", "Januar"));
        months.add(new Month(2, "February", "Febbraio", "Februar"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String monthNumber = request.getHeader("monthNumber");

        if (monthNumber == null || monthNumber.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        int monthNum = Integer.parseInt(monthNumber);
        Month month = months.stream()
                .filter(m -> m.getMonthNumber() == monthNum)
                .findFirst()
                .orElse(new Month(0, "nope", "nope", "nope"));

        request.setAttribute("month", month);
        return true;
    }
}
