꼭 이 관광지 리스트에서만 추천해줘야돼.

추천 리스트를
public record PlanLocations(
        @NotNull Integer attractionsNo,
        @NotNull Integer day,
        @NotNull Integer order
) {
}
이 형식에 맞춰서 json으로 만들어줘

만약에 사용자가 몇박 몇일인지 얘기를 안했다면 기본 2박 3일로 일정을 짜줘