import { UserType } from "../../../model/user";
import { CV, CVStatus } from "../../../model/cv";
import { Button } from "react-bootstrap";
import CvButtons from "./CvButtons";


interface Props {
    cv: CV;
    userType: UserType;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const CvRow = ({cv, userType, updateCvsState}: Props) => {    
    const showCv = () => {
        console.log(cv); //TODO : Actually something that shows the CV in a modal or something
    }

    return (
        <>
            <tr className="hovered">
                <td>{cv.fileName}</td>
                <td><Button onClick={showCv}>I18N</Button></td>
                <td className="text-end"><CvButtons userType={userType} disabled={false} cv={cv} updateCvsState={updateCvsState}/></td>
            </tr>
        </>
    ); //TODO : I18N where I18N is needed
};

export default CvRow;