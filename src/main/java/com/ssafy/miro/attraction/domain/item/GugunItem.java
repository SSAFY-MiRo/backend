package com.ssafy.miro.attraction.domain.item;

import com.ssafy.miro.attraction.domain.Gugun;

public record GugunItem(
        Integer gugunCode,
        String gugunName
) {
    public static GugunItem of(Gugun gugun) {
        return new GugunItem(gugun.getGugunCode(), gugun.getGugunName());
    }
}
