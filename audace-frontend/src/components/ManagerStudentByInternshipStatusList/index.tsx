import {useEffect, useState} from "react";
import {StudentsByInternshipFoundStatus} from "../../model/user";
import {getStudentsByInternshipStatus} from "../../services/managerService";

const ManagerStudentByInternshipStatusList = () => {
    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<StudentsByInternshipFoundStatus>();

    useEffect(() => {
        getStudentsByInternshipStatus(1).then((res) => {
            setStudentsByInternshipStatus(res.data);
        });
    }, [studentsByInternshipStatus]);

    return (
        <div>
            ManagerStudentByInternshipStatusList
        </div>
    )
}

export default ManagerStudentByInternshipStatusList