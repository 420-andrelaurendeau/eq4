import './styles.css'

interface Props {
    errors: string[];
}

const FormError = ({errors}: Props) => {
    return (
        <>
            {errors.map((error, index) => (
                <p key={index} className="error">{error}</p>
            ))}
        </>
    )
}

export default FormError;