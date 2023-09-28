import { Button } from "react-bootstrap";
import { UserType } from "../../../../model/user";

interface Props {
    userType : UserType;
    disabled? : boolean;
}

const OfferButtons = ({userType, disabled} : Props) => {
    const applyButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const acceptButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const refuseButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const editButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const deleteButtonClick = (event : React.MouseEvent<HTMLElement>) => {
        event.stopPropagation();
    }

    const studentRow = () => {//TODO : Check the style
        return (
            <>
                <Button disabled={disabled} onClick={applyButtonClick}>Apply</Button>
            </>
        )
    }

    const managerRow = () => {
        return (
            <>
                <Button disabled={disabled} onClick={acceptButtonClick} className="btn-success me-2">Accept</Button>
                <Button disabled={disabled} onClick={refuseButtonClick} className="btn-danger">Refuse</Button>
            </>
        )
    }

    const employerRow = () => {
        return (
            <>
                <Button disabled={disabled} onClick={editButtonClick} className="btn-warning me-2">Edit</Button>
                <Button disabled={disabled} onClick={deleteButtonClick} className="btn-danger">Delete</Button>
            </>
        )
    }

    const changingRow = () => { //Name in progress
        switch (userType) {
            case UserType.Student:
                return studentRow();
            case UserType.Manager:
                return managerRow();
            case UserType.Employer:
                return employerRow();
        }        
    }

    return (
        <>
            {changingRow()}
        </>
    );
}

export default OfferButtons;