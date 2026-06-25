package com.timetable.timetable_backend.service;

import com.timetable.timetable_backend.entity.User;

public interface UserService {

	User getByUsername(String username);

	boolean existsByUsername(String username);

	void save(User user);

	java.util.List<User> getPendingUsers(Long instituteId);

	void approveUser(Long userId);

	void rejectUser(Long userId);
}
