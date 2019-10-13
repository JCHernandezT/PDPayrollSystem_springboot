package co.com.juan.poly.service.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Helper to simplify accessing i18n messages in code.
 * 
 * This finds messages automatically found from src/main/resources (files named messages_*.properties)
 *
 * @author Juan Hernández
 */
@Component
public class MessagesService {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
    	// LocaleContextHolder returns: Locale.XXXX
        accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }
    
    public String get(String code, Object[] params) {
    	return accessor.getMessage(code, params, LocaleContextHolder.getLocale());
    }

}