package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskOutputDTO createTask(TaskInputDTO taskInputDTO);

    List<TaskOutputDTO> getAllTasks();

    Optional<TaskOutputDTO> getTaskById(Long id);

    TaskOutputDTO updateTask(Long id, TaskInputDTO taskInputDTO);

    void deleteTask(Long id);
}
