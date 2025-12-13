package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userStatsDto {
    private Integer totalUsers;
    private Integer totalOfficers;
    private Integer totalAdmins;
    private Integer totalRegularUsers;
}
