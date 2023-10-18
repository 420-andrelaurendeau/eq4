import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { getStudentById } from "../../services/userService";
import { useTranslation } from "react-i18next";
import { Student, UserType } from "../../model/user";
import { Offer } from "../../model/offer";
import { getStudentOffersByDepartment } from "../../services/offerService";
import OffersList from "../../components/OffersList";
import { getUserId } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import FileUploader from "../../components/FileUploader";
import {getCvsByStudentId} from "../../services/studentApplicationService";
import {CV} from "../../model/cv";
import CvsList from "../../components/CVsListStudent";

const StudentView = () => {
    const [student, setStudent] = useState<Student>();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [offersError, setOffersError] = useState<string>("");
    const [cvs, setCvs] = useState<CV[]>([]);
    const [cvsError, setCvsError] = useState<string>("");
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        if (student !== undefined) return;

        const id = getUserId();

        if (id == null) {
            navigate("/pageNotFound");
            return;
        }

        getStudentById(parseInt(id))
            .then((res) => {
                setStudent(res.data);
            })
            .catch((err) => {
                console.error(err);
                if (err.request && err.request.status === 404) {
                    setOffersError(t("studentOffersList.errors.studentNotFound"));
                    setCvsError(t("studentOffersList.errors.studentNotFound")); // Set both errors for consistency
                }
            });
    }, [student, navigate, t]);

    useEffect(() => {
        if (student === undefined) return;

        getStudentOffersByDepartment(student.department!.id!)
            .then((res) => {
                setOffers(res.data);
            })
            .catch((err) => {
                console.error(err);

                if (err.request && err.request.status === 404) {
                    setOffersError(t("offersList.errors.departmentNotFound"));
                }
            });

        getCvsByStudentId(student.id!)
            .then((res) => {
                if (res.data.length === 0) {
                    setCvsError(t("cvsList.noCvs"));
                } else {
                    setCvs(res.data);
                }
            })
            .catch((err) => {
                console.error(err);

                if (err.request && err.request.status === 404) {
                    setCvsError(t("cvsList.noCvs"));
                }
            });
    }, [student, t]);

    return (
        <Container>
            <h1 className="my-3">Student view</h1>
            <h2>{t("studentOffersList.viewTitle")}</h2>
            <OffersList offers={offers} error={offersError} userType={UserType.Student}/>

            {cvs.length > 0 ? (
                <CvsList cvs={cvs} error={cvsError} userType={UserType.Student}/>
            ) : (
                <><h2>CV's</h2><p>{cvsError}</p></>
            )}

            <FileUploader student={student!}/>
        </Container>
    );
};

export default StudentView;
