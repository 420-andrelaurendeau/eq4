import './styles.css'

interface Props {
    errors: string[];
}

const FormError = ({errors}: Props) => {
    return (
        <>
            <div className="error">
                {errors.map((error, index) => (
                    <p key={index} className="text">{error}</p>
                ))}
            </div>
        </>
    )
}

export default FormError;