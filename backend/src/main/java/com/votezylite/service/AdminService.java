package com.votezylite.service;

import com.votezylite.dto.AdminDashboardDto;

public interface AdminService {
    AdminDashboardDto dashboard(Long electionId);
}
