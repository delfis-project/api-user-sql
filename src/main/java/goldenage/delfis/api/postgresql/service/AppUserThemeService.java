/*
 * Classe AppUserThemeService
 * Service da entidade AppUserTheme
 * Autor: João Diniz Araujo
 * Data: 19/08/2024
 * */

package goldenage.delfis.api.postgresql.service;

import goldenage.delfis.api.postgresql.model.AppUserTheme;
import goldenage.delfis.api.postgresql.repository.AppUserThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserThemeService {
    private final AppUserThemeRepository appUserThemeRepository;

    public AppUserThemeService(AppUserThemeRepository appUserThemeRepository) {
        this.appUserThemeRepository = appUserThemeRepository;
    }

    /**
     * @return todos os appUserThemes do banco.
     */
    public List<AppUserTheme> getAppUserThemes() {
        List<AppUserTheme> appUserThemes = appUserThemeRepository.findAll();
        return appUserThemes.isEmpty() ? null : appUserThemes;
    }

    /**
     * @return appUserTheme pelo id.
     */
    public AppUserTheme getAppUserThemeById(Long id) {
        Optional<AppUserTheme> appUserTheme = appUserThemeRepository.findById(id);
        return appUserTheme.orElse(null);  // vai retornar appUserTheme se ele existir, se não retorna null
    }

    /**
     * @return appUserTheme pelo appUser.
     */
    public List<AppUserTheme> getAppUserThemesByAppUserId(Long id) {
        List<AppUserTheme> appUserThemes = appUserThemeRepository.findAppUserThemesByFkAppUserId(id);
        return !appUserThemes.isEmpty() ? appUserThemes : null;
    }

    /**
     * @return appUserTheme deletado.
     */
    public AppUserTheme deleteAppUserThemeById(Long id) {
        AppUserTheme appUserTheme = getAppUserThemeById(id);
        if (appUserTheme != null) appUserThemeRepository.deleteById(id);
        return appUserTheme;
    }

    /**
     * @return appUserTheme inserido.
     */
    public AppUserTheme saveAppUserTheme(AppUserTheme appUserTheme) {
        return appUserThemeRepository.save(appUserTheme);
    }
}
