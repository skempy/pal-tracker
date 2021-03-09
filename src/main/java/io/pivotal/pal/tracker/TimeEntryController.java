package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdEntry = timeEntryRepository.create(timeEntryToCreate);
        return status(CREATED).body(createdEntry);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        return handleWhenThereIsNo(timeEntry);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> listOfTimeEntries = timeEntryRepository.list();
        return status(OK).body(listOfTimeEntries);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
        return handleWhenThereIsNo(timeEntry);
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return status(NO_CONTENT).build();
    }

    private ResponseEntity<TimeEntry> handleWhenThereIsNo(TimeEntry timeEntry) {
        if (timeEntry == null) {
            return status(NOT_FOUND).build();
        }
        return status(OK).body(timeEntry);
    }
}
