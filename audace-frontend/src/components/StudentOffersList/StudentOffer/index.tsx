import { Offer } from "../../../model/offer";

interface Props {
    offer: Offer;
}

const StudentOffer = ({offer}: Props) => {
    const formatDate = (date: Date) => {
        const newDate = new Date(date);
        const year = newDate.getFullYear();
        const month = newDate.getMonth() + 1;
        const day = newDate.getDate();

        return `${day}/${month}/${year}`;
    }

    return (
        <>
            <tr>
                <td>{offer.title}</td>
                <td>{formatDate(offer.internshipStartDate)}</td>
                <td>{formatDate(offer.internshipEndDate)}</td>
            </tr>
        </>
    );
};

export default StudentOffer;