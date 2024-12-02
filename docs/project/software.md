# Реалізація інформаційного та програмного забезпечення

## SQL-скрипт для створення на початкового наповнення бази даних

```sql
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE TABLE IF NOT EXISTS `User` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `roleId` INT UNSIGNED NOT NULL,
  `status` ENUM('ACTIVE', 'BANNED') NOT NULL DEFAULT 'ACTIVE',
  `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email`),
  INDEX `roleId_idx` (`roleId`),
  CONSTRAINT `fk_roleId`
      FOREIGN KEY (`roleId`)
          REFERENCES `Role` (`id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Role` ;

CREATE TABLE IF NOT EXISTS `Role` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idRole_UNIQUE` (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Project`;

CREATE TABLE IF NOT EXISTS `Project` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `ownerId` INT UNSIGNED NOT NULL,
    `teamId` INT UNSIGNED NOT NULL,
    `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name`),
    INDEX `ownerId_idx` (`ownerId`),
    CONSTRAINT `fk_ownerId`
        FOREIGN KEY (`ownerId`)
            REFERENCES `User` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT `fk_teamId`
        FOREIGN KEY (`teamId`)
            REFERENCES `Team` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Team`;

CREATE TABLE IF NOT EXISTS `Team` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Member`;

CREATE TABLE IF NOT EXISTS `Member` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `userId` INT UNSIGNED NOT NULL,
    `teamId` INT UNSIGNED NOT NULL,
    `teamRole` ENUM('Developer', 'Project Leader') NOT NULL DEFAULT 'Developer',
    `joinedAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `userId_idx` (`userId`),
    INDEX `teamId_idx` (`teamId`),
    CONSTRAINT `fk_userId`
        FOREIGN KEY (`userId`)
            REFERENCES `User` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_member_teamId`
        FOREIGN KEY (`teamId`)
            REFERENCES `Team` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Task`;

CREATE TABLE IF NOT EXISTS `Task` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `assignedTo` INT UNSIGNED DEFAULT NULL,
  `projectId` INT UNSIGNED NOT NULL,
  `status` ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'ON_HOLD', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
  `priority` ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL DEFAULT 'MEDIUM',
  `dueDate` DATETIME NULL,
  `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `assignedTo_idx` (`assignedTo`),
  INDEX `projectId_idx` (`projectId`),
  CONSTRAINT `fk_assignedTo_user`
      FOREIGN KEY (`assignedTo`)
          REFERENCES `User` (`id`)
          ON DELETE SET NULL
          ON UPDATE CASCADE,
  CONSTRAINT `fk_projectId`
      FOREIGN KEY (`projectId`)
          REFERENCES `Project` (`id`)
          ON DELETE CASCADE
          ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Artefact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Artefact`;

CREATE TABLE IF NOT EXISTS `Artefact` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `filePath` VARCHAR(255) NOT NULL,
  `fileType` VARCHAR(45) NOT NULL,
  `uploadedBy` INT UNSIGNED NOT NULL,
  `projectId` INT UNSIGNED NOT NULL,
  `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `projectId_idx` (`projectId`),
  CONSTRAINT `fk_uploadedBy_user`
      FOREIGN KEY (`uploadedBy`)
          REFERENCES `User` (`id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION,
  CONSTRAINT `fk_projectId_artefact`
      FOREIGN KEY (`projectId`)
          REFERENCES `Project` (`id`)
          ON DELETE CASCADE
          ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`Grant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Grant`;

CREATE TABLE IF NOT EXISTS `Grant` (
   `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
   `projectId` INT UNSIGNED NOT NULL,
   `userId` INT UNSIGNED NOT NULL,
   `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   INDEX `projectId_idx` (`projectId`),
   INDEX `userId_idx` (`userId`),
   CONSTRAINT `fk_grant_project`
       FOREIGN KEY (`projectId`)
           REFERENCES `Project` (`id`)
           ON DELETE CASCADE
           ON UPDATE CASCADE,
   CONSTRAINT `fk_grant_user`
       FOREIGN KEY (`userId`)
           REFERENCES `User` (`id`)
           ON DELETE CASCADE
           ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Filling the tables with data
START TRANSACTION;

INSERT INTO `Role` (`name`) VALUES
('Admin'),
('Developer'),
('Manager');

INSERT INTO `User` (`username`, `email`, `password`, `roleId`, `status`) VALUES
('john_doe', 'john.doe@example.com', 'password123', 1, 'ACTIVE'),
('jane_smith', 'jane.smith@example.com', 'password123', 2, 'ACTIVE'),
('alex_williams', 'alex.williams@example.com', 'password123', 3, 'ACTIVE'),
('michael_brown', 'michael.brown@example.com', 'password123', 2, 'BANNED');

INSERT INTO `Team` () VALUES
(),
(),
(),
();

INSERT INTO `Member` (`userId`, `teamId`, `teamRole`) VALUES
(1, 1, 'Project Leader'),
(2, 1, 'Developer'),
(3, 2, 'Developer'),
(4, 3, 'Developer');

INSERT INTO `Project` (`name`, `description`, `ownerId`, `teamId`) VALUES
('Project A', 'Description for Project A', 1, 1),
('Project B', 'Description for Project B', 3, 2),
('Project C', 'Description for Project C', 1, 3);

INSERT INTO `Task` (`title`, `description`, `assignedTo`, `projectId`, `status`, `priority`, `dueDate`) VALUES
('Task 1 for Project A', 'Task 1 description', 2, 1, 'PENDING', 'HIGH', '2024-11-20 10:00:00'),
('Task 2 for Project A', 'Task 2 description', 3, 1, 'IN_PROGRESS', 'MEDIUM', '2024-11-25 12:00:00'),
('Task 1 for Project B', 'Task 1 description', 4, 2, 'PENDING', 'LOW', '2024-11-22 09:00:00'),
('Task 1 for Project C', 'Task 1 description', 2, 3, 'COMPLETED', 'HIGH', '2024-11-15 16:00:00');

INSERT INTO `Artefact` (`title`, `description`, `filePath`, `fileType`, `uploadedBy`, `projectId`) VALUES
('Artefact 1 for Project A', 'Initial design file', '/files/project_a/design_v1.pdf', 'PDF', 2, 1),
('Artefact 2 for Project B', 'Final report for Project B', '/files/project_b/report_final.pdf', 'PDF', 4, 2),
('Artefact 1 for Project C', 'Codebase for Project C', '/files/project_c/code.zip', 'ZIP', 2, 3);

INSERT INTO `Grant` (`projectId`, `userId`) VALUES
(1, 2),
(1, 3),
(2, 4);

COMMIT;
```
## RESTfull сервіс для управління даними
## Entities

### Team Entity
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```
### Project Entity
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Artefact> artefacts = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Grant> grants;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```
### Task Entity
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority = TaskPriority.MEDIUM;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED
    }

    public enum TaskPriority {
        LOW, MEDIUM, HIGH
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

```
### Team Service Implementaion
```java
package com.example.DB.Service.impl;

import com.example.DB.Service.TeamService;
import com.example.DB.dto.TeamDto;
import com.example.DB.entity.Team;
import com.example.DB.entity.Member;
import com.example.DB.entity.Project;
import com.example.DB.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamDto createTeam() {
        Team team = new Team();
        Team savedTeam = teamRepository.save(team);
        return convertToDto(savedTeam);
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return convertToDto(team);
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsByMember(Long userId) {
        return teamRepository.findByMembersUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsByProject(Long projectId) {
        return teamRepository.findByProjectsId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found");
        }
        teamRepository.deleteById(id);
    }

    private TeamDto convertToDto(Team team) {
        return new TeamDto(
                team.getId(),
                team.getCreatedAt(),
                team.getMembers() != null ?
                        team.getMembers().stream().map(Member::getId).collect(Collectors.toList()) :
                        new ArrayList<>(),
                team.getProjects() != null ?
                        team.getProjects().stream().map(Project::getId).collect(Collectors.toList()) :
                        new ArrayList<>()
        );
    }
}
```
### Project Service Implementaion
```java
package com.example.DB.Service.impl;

import com.example.DB.Service.ProjectService;
import com.example.DB.dto.ProjectDto;
import com.example.DB.entity.Project;
import com.example.DB.entity.Team;
import com.example.DB.entity.User;
import com.example.DB.entity.Task;
import com.example.DB.entity.Artefact;
import com.example.DB.repository.ProjectRepository;
import com.example.DB.repository.TeamRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setOwner(userRepository.findById(projectDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found")));
        project.setTeam(teamRepository.findById(projectDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found")));
        project.setTasks(new ArrayList<>());
        project.setArtefacts(new ArrayList<>());
        project.setCreatedAt(LocalDateTime.now());

        return convertToDto(projectRepository.save(project));
    }

    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        Project updatedProject = projectRepository.save(project);
        return convertToDto(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectDto convertToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getTeam().getId(),
                project.getCreatedAt(),
                project.getTasks().stream().map(Task::getId).collect(Collectors.toList()),
                project.getArtefacts().stream().map(Artefact::getId).collect(Collectors.toList())
        );
    }
}
```
### Task Service Implementaion
```java
package com.example.DB.Service.impl;

import com.example.DB.Service.TaskService;
import com.example.DB.dto.TaskDto;
import com.example.DB.entity.Project;
import com.example.DB.entity.Task;
import com.example.DB.entity.User;
import com.example.DB.repository.ProjectRepository;
import com.example.DB.repository.TaskRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskDto createTask(TaskDto taskDto) {
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setProject(project);
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());

        if (taskDto.getAssignedToId() != null) {
            User assignee = userRepository.findById(taskDto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignedTo(assignee);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToDto(task);
    }

    public List<TaskDto> getProjectTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getUserTasks(Long userId) {
        return taskRepository.findByAssignedToId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDto updateTaskStatus(Long id, Task.TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return convertToDto(taskRepository.save(task));
    }

    public TaskDto assignTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAssignedTo(assignee);
        return convertToDto(taskRepository.save(task));
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null,
                task.getProject().getId(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt()
        );
    }
}
```
### Team Controller
```java
package com.example.DB.controller;

import com.example.DB.Service.TeamService;
import com.example.DB.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto createTeam() {
        return teamService.createTeam();
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/member/{userId}")
    public List<TeamDto> getTeamsByMember(@PathVariable Long userId) {
        return teamService.getTeamsByMember(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<TeamDto> getTeamsByProject(@PathVariable Long projectId) {
        return teamService.getTeamsByProject(projectId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}

```
### Project Controller
```java
package com.example.DB.controller;

import com.example.DB.Service.ProjectService;
import com.example.DB.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/owner/{ownerId}")
    public List<ProjectDto> getProjectsByOwner(@PathVariable Long ownerId) {
        return projectService.getProjectsByOwner(ownerId);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
```
### Task Controller
```java
package com.example.DB.controller;

import com.example.DB.Service.TaskService;
import com.example.DB.dto.TaskDto;
import com.example.DB.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> getProjectTasks(@PathVariable Long projectId) {
        return taskService.getProjectTasks(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskDto> getUserTasks(@PathVariable Long userId) {
        return taskService.getUserTasks(userId);
    }

    @PatchMapping("/{id}/status")
    public TaskDto updateTaskStatus(@PathVariable Long id, @RequestBody Task.TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }

    @PatchMapping("/{id}/assign/{userId}")
    public TaskDto assignTask(@PathVariable Long id, @PathVariable Long userId) {
        return taskService.assignTask(id, userId);
    }
}
```
### Team Repository
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

```
### Project Repository
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Artefact> artefacts = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Grant> grants;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```
### Task Repository
```java
package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority = TaskPriority.MEDIUM;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED
    }

    public enum TaskPriority {
        LOW, MEDIUM, HIGH
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


```
### Main application
```java
package com.example.DB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbApplication.class, args);
	}

}
```
