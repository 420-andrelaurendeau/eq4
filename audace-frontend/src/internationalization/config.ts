import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

export type Locale = "en" | "fr";
export const ALL_LOCALES: Locale[] = ["en", "fr"];
let lang = 'fr';
if (typeof window !== 'undefined') {
    lang = localStorage.getItem('lang') || 'fr';
}

i18n.use(initReactI18next).init({
    fallbackLng: lang,
    lng: lang,
    resources: {
        en: {
            translation: require('./locales/en.json')
        },
        fr: {
            translation: require('./locales/fr.json')
        }
    },
    ns: ['translation'],
    defaultNS: 'translation',
})

i18n.languages = ['en', 'fr'];

export default i18n;