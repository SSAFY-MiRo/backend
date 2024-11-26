내가 관광지 리스트를 줄게, 꼭 이 관광지에서만 추천해줘야돼.

추천 리스트를
public record PlanLocations(
        @NotNull Integer attractionsNo,
        @NotNull Integer day,
        @NotNull Integer order
) {
}
이 형식에 맞춰서 json으로 만들어줘