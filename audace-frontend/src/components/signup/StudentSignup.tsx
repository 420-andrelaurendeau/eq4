import { Button, Form } from "react-bootstrap"

const StudentSignup = () => {
    const handleSubmit = () => {
        // temp
    }

    return (
        <>
            <h3>Student Signup</h3>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control type="email" placeholder="Enter email"/>
                </Form.Group>

                <Form.Group controlId="formBasicStudentId">
                    <Form.Label>Student ID</Form.Label>
                    <Form.Control type="text" placeholder="Enter student ID"/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password"/>
                </Form.Group>

                <Button variant="primary" type="submit" className="mt-3" onSubmit={handleSubmit}>
                    Submit
                </Button>
            </Form>
        </>
    )
}

export default StudentSignup