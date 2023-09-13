import { useContext } from "react";
import { LanguageContext, LanguageContextHolder } from "../contextsholders/languageContextHolder";
import { Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const LanguageToggler = () => {
    const {toggleLanguage} = useContext<LanguageContextHolder>(LanguageContext);
    const {t} = useTranslation();

    return (
        <Button onClick={toggleLanguage}>
            {t("langCode")}
        </Button>
    )
};

export default LanguageToggler;