package ng.com.bitsystems.digitalsignature.services;

import ng.com.bitsystems.digitalsignature.command.SessionCommand;
import ng.com.bitsystems.digitalsignature.model.Sessions;

public interface SessionService extends CrudService<Sessions, Long> {
    SessionCommand getSessionCommandById(Long id);
}
