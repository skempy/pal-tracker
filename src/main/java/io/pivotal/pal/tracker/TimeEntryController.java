package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
        TimeEntry foundTimeEntry = timeEntryRepository.find(timeEntryId);
        if (foundTimeEntry == null) {
            return status(NOT_FOUND).build();
        }
        return status(OK).body(foundTimeEntry);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> listOfTimeEntries = timeEntryRepository.list();
        return status(OK).body(listOfTimeEntries);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry foundTimeEntry = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
        if (foundTimeEntry == null) {
            return status(NOT_FOUND).build();
        }
        return status(OK).body(foundTimeEntry);
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return status(NO_CONTENT).build();
    }
}
