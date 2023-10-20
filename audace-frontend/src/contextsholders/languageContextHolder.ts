import i18next from "i18next";
import {createContext} from "react";
import { ALL_LOCALES, Locale } from "../internationalization/config";

export class LanguageContextHolder {
    public currentLanguage;

    constructor() {
        this.currentLanguage = 'en';
    }

    public setLanguage = (languageCode: string): void => {
        this.currentLanguage = languageCode;
        localStorage.setItem('lang', this.currentLanguage);
        i18next.changeLanguage(languageCode);
    }

    public toggleLanguage = (): void => {
        let nextLanguage = this.nextLanguage();
        this.setLanguage(nextLanguage);
    }

    public nextLanguage = (): string => {
        let nextIndex = (ALL_LOCALES.indexOf(this.currentLanguage as Locale) + 1) % ALL_LOCALES.length;
        return ALL_LOCALES[nextIndex];
    }
}

export const languageContextHolderInstance = new LanguageContextHolder();
export const LanguageContext = createContext<LanguageContextHolder>(languageContextHolderInstance);