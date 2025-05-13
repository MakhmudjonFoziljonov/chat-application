package com.chat.userservice.service;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class ResourceBundleServiceImpl implements ResourceBundleService {
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public ResourceBundleServiceImpl(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public String getMessage(String code, Locale locale) {
        return resourceBundleMessageSource.getMessage(code, new Object[]{}, locale);
    }

}
