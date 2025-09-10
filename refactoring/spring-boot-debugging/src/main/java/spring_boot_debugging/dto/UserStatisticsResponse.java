package spring_boot_debugging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatisticsResponse {
    private long totalUsers;
    private long adultUsers;
    private double adultUsersPercentage;
}