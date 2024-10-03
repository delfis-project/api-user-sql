/*
 * Classe SessionService
 * Service da entidade Session
 * Autor: João Diniz Araujo
 * Data: 16/09/2024
 * */

package goldenage.delfis.api.redis.service;

import goldenage.delfis.api.redis.model.Session;
import goldenage.delfis.api.redis.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    public List<Session> getSessions() {
        List<Session> sessions = new ArrayList<>();
        sessionRepository.findAll().forEach(sessions::add);
        return sessions;
    }

    public Session getUnfinishedSessionByFkAppUserById(long fkAppUserId) {
        List<Session> sessions = getSessions();

        return sessions.stream()
                .filter(session -> session.getFkAppUserId() == fkAppUserId && session.getFinalDatetime() == null)
                .sorted((s1, s2) -> s2.getInitialDatetime().compareTo(s1.getInitialDatetime()))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteSession(String id) {
        sessionRepository.deleteById(id);
        return sessionRepository.findById(id).isEmpty();
    }

    public Session finishSession(String sessionId) {
        return sessionRepository.findById(sessionId).map(existingSession -> {
            existingSession.setFinalDatetime(LocalDateTime.now());
            return sessionRepository.save(existingSession);
        }).orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada com ID: " + sessionId));
    }
}