import { Offer } from "../../model/offer";
import Application, { ApplicationStatus } from "../../model/application";
import { Container } from "react-bootstrap";
import ApplicationsList from "../ApplicationsList";

interface Props {
  applications: Application[];
  offer: Offer;
  error: string;
  updateAvailablePlaces?: (offer: Offer) => void;
  updateApplicationsState?: (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => void;
}

const Applications = ({
  offer,
  applications,
  error,
  updateApplicationsState,
}: Props) => {
  return (
    <Container>
      {
        <ApplicationsList
          offer={offer}
          applications={applications}
          error={error}
          updateApplicationsState={updateApplicationsState}
        />
      }
    </Container>
  );
};

export default Applications;
