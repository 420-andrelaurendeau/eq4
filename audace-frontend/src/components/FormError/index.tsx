import { useTranslation } from 'react-i18next';
import './styles.css'

interface Props {
    errors: string[];
}

const FormError = ({errors}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            {errors.map((error, index) => (
                <p key={index} className="error">{t(error)}</p>
            ))}
        </>
    )
}

export default FormError;