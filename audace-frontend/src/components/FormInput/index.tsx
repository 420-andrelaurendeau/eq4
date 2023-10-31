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
    type?: string;
    placeholder?: string;
}

const FormInput = ({label, value, onChange, errors, formError, controlId, type, placeholder}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            <Col sm={12} md={true}>
                <Form.Group controlId={controlId}>
                    <Form.Label htmlFor={controlId}>{t(label)}</Form.Label>
                    <Form.Control
                        type={type ? type : "text"}
                        value={value}
                        onChange={onChange}
                        placeholder={placeholder ? placeholder : undefined}
                    />
                    {errors.includes(formError) ?
                        <p className="error fade-in">{t(formError)}</p> : 
                        <div className="error"/>
                    }
                </Form.Group>
            </Col>
        </>
    )
};

export default FormInput;