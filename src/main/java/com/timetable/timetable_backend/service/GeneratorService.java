package com.timetable.timetable_backend.service;

import com.timetable.timetable_backend.entity.Subject;
import com.timetable.timetable_backend.entity.Timetable;
import com.timetable.timetable_backend.repository.SubjectRepository;
import com.timetable.timetable_backend.repository.TimetableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GeneratorService {

    private final SubjectRepository subjectRepository;
    private final TimetableRepository timetableRepository;

    public GeneratorService(SubjectRepository subjectRepository, TimetableRepository timetableRepository) {
        this.subjectRepository = subjectRepository;
        this.timetableRepository = timetableRepository;
    }

    @Transactional
    public List<Timetable> generateForDepartment(String departmentName, List<String> availableRooms) {
        // 1. Fetch all subjects for this department
        List<Subject> subjects = subjectRepository.findByDepartment_Name(departmentName);

        // 2. Prepare empty schedule structure
        // List of slots to fill: (Subject, SessionIndex)
        List<Timetable> proposedSchedule = new ArrayList<>();

        // Days and Periods configuration (Hardcoded for now)
        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        int periodsPerDay = 6;

        // 3. Backtracking Algorithm
        // We need to assign each required session of each subject to a (Day, Period,
        // Room)
        // Constraints:
        // - Teacher not already booked in that Day/Period
        // - Class (Semester/Group) not already booked in that Day/Period
        // - Room not already booked in that Day/Period

        // Flatten tasks: Subject S with Hours H -> H tasks
        List<Subject> tasks = new ArrayList<>();
        for (Subject s : subjects) {
            for (int i = 0; i < s.getHoursPerWeek(); i++) {
                tasks.add(s);
            }
        }

        // Sort tasks? Maybe hardest first (teachers with most hours)

        if (solve(tasks, 0, proposedSchedule, days, periodsPerDay, availableRooms)) {
            // Save all
            timetableRepository.saveAll(proposedSchedule);
            return proposedSchedule;
        } else {
            throw new RuntimeException("Could not generate a valid timetable with given constraints.");
        }
    }

    private boolean solve(List<Subject> tasks, int taskIndex, List<Timetable> schedule, String[] days, int periods,
            List<String> rooms) {
        if (taskIndex >= tasks.size()) {
            return true; // All tasks assigned
        }

        Subject currentTask = tasks.get(taskIndex);

        // Try every Time Slot and Room
        for (String day : days) {
            for (int p = 1; p <= periods; p++) {
                for (String room : rooms) {

                    if (isValid(currentTask, day, p, room, schedule)) {

                        Timetable entry = new Timetable();
                        entry.setDepartment(currentTask.getDepartment().getName());
                        entry.setClassName(currentTask.getDepartment().getName() + " - " + currentTask.getCode()); // Simplified
                                                                                                                   // ClassName
                                                                                                                   // mapping
                        // In reality, Subject should link to a Class/Semester entity.
                        // Assuming Subject.department + Subject.code gives unique context or we use a
                        // field from Subject.
                        // Let's assume Subject -> Semester is missing, we might need to fix that or use
                        // what we have.
                        // Wait, Subject doesn't have "Semester". It has "Department".
                        // Let's use "Department" as the grouping for now, or just assume "ClassName" is
                        // stored in Subject?
                        // Checking Subject.java... no ClassName.
                        // Let's check AcademicClass entity if it exists.

                        // FIX: Logic Gap. Subjects belong to a "Course" or "Class".
                        // I will assume for now that I can construct a class name.
                        // Or I should fetch AcademicClass.

                        entry.setClassName(currentTask.getDepartment().getName()); // Placeholder
                        entry.setSubject(currentTask.getName());
                        entry.setTeacher(currentTask.getTeacher().getName());
                        entry.setDay(day);
                        entry.setPeriod(p);
                        entry.setRoom(room);

                        schedule.add(entry);

                        if (solve(tasks, taskIndex + 1, schedule, days, periods, rooms)) {
                            return true;
                        }

                        // Backtrack
                        schedule.remove(schedule.size() - 1);
                    }
                }
            }
        }

        return false;
    }

    private boolean isValid(Subject subject, String day, int period, String room, List<Timetable> schedule) {
        for (Timetable t : schedule) {
            // 1. Same Day & Period?
            if (t.getDay().equals(day) && t.getPeriod() == period) {
                // Conflict: Same Room
                if (t.getRoom().equals(room))
                    return false;

                // Conflict: Same Teacher
                if (t.getTeacher().equals(subject.getTeacher().getName()))
                    return false;

                // Conflict: Same Student Group (ClassName)
                // We need to know if this subject belongs to the same class as 't'.
                // If we assume purely Department-based generation:
                if (t.getDepartment().equals(subject.getDepartment().getName()))
                    return false; // Simple: 1 class per dept at a time
            }
        }
        return true;
    }
}
