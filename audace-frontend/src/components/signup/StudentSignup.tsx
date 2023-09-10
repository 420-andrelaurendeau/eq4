import { useState } from "react"
import { Button, Form } from "react-bootstrap"
import { studentSignup } from "../../services/signupService"
import { Student } from "../../model/user"

const StudentSignup = () => {
    const [email, setEmail] = useState<string>("")
    const [studentId, setStudentId] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const handleSubmit = () => {
        if (!validateForm())
            return;

        let student: Student = {
            email: email,
            studentId: studentId,
            password: password
        }

        studentSignup(student)
            .then((res) => {})
            .catch((err) => {})
    }

    const validateForm = (): boolean => {
        return email !== "" && studentId !== "" && password !== ""
    }

    return (
        <>
            <h3>Student Signup</h3>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control 
                        type="email" 
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicStudentId">
                    <Form.Label>Student ID</Form.Label>
                    <Form.Control 
                        type="text" 
                        placeholder="Enter student ID"
                        value={studentId}
                        onChange={(e) => setStudentId(e.target.value)}
                        />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control 
                        type="password" 
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>

                <Button variant="primary" type="submit" className="mt-3" onSubmit={handleSubmit}>
                    Submit
                </Button>
            </Form>
        </>
    )
}

export default StudentSignup