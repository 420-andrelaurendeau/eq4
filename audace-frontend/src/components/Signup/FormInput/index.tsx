import { Col, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import './styles.css'

interface Props {
    label: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    errors: string[];
    formError: string;
    controlId: string;
}

const FormInput = ({label, value, onChange, errors, formError, controlId}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            <Form.Group as={Col} controlId={controlId}>
                <Form.Label>{t(label)}</Form.Label>
                <Form.Control
                    type="text"
                    value={value}
                    onChange={onChange}
                />
                {errors.includes(formError) ?
                    <p className="error fade-in">{t(formError)}</p> : 
                    <div className="error"/>
                }
            </Form.Group>
        </>
    )
};

export default FormInput;