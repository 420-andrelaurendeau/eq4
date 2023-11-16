import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import EditOffer from '../../components/EditOffer';
import '@testing-library/jest-dom/extend-expect';
import * as departmentService from '../../services/departmentService';
import * as offerService from '../../services/offerService';
import { Offer, OfferStatus } from '../../model/offer';
import { Department } from '../../model/department';
import { AxiosResponse } from 'axios';

jest.mock('../../services/offerService', () => ({
  ...jest.requireActual('../../services/offerService'), 
  getEmployersOfferById: jest.fn(), 
  employerUpdateOffer: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ offerId: '1' }),
    useNavigate: () => jest.fn(),
}));

const mockDepartments = [
    { id: 1, code: 'GLO', name: 'Genie' },
];

jest.mock('../../services/departmentService', () => ({
    ...jest.requireActual('../../services/departmentService'),
    getAllDepartments: jest.fn(() => Promise.resolve({
        data: mockDepartments,
    })),
}));

const mockEmployer = {
    id: 1,
    name: 'Test Employer',
    organisation: 'Test Organisation',
    position: 'Test Position',
    extension: '1234',
    email: 'test@test.com',
    password: 'testpassword'
};

const mockOffer = {
    id: 1,
    title: 'Existing Title',
    description: 'Existing Description',
    department: mockDepartments[0],
    internshipStartDate: new Date().toISOString(),
    internshipEndDate: new Date().toISOString(),
    offerEndDate: new Date().toISOString(),
    availablePlaces: 3,
    offerStatus: OfferStatus.PENDING,
    employer: mockEmployer,
};

describe('EditOffer', () => {

    beforeEach(() => {
        (departmentService.getAllDepartments as jest.MockedFunction<typeof departmentService.getAllDepartments>).mockResolvedValue(Promise.resolve({ data: mockDepartments }) as unknown as AxiosResponse<Department[]>);
        (offerService.getEmployersOfferById as jest.MockedFunction<typeof offerService.getEmployersOfferById>).mockResolvedValue(Promise.resolve({ data: mockOffer }) as unknown as AxiosResponse<Offer>);
        jest.clearAllMocks();
    });


    it('loads existing offer data on start', async () => {
        render(<EditOffer />);

        await waitFor(() => {
            expect(screen.getByDisplayValue('Existing Title')).toBeInTheDocument();
            expect(screen.getByDisplayValue('Existing Description')).toBeInTheDocument();
        });
    });

    it('shows validation errors on submit with invalid data', async () => {
        render(<EditOffer />);

        fireEvent.click(screen.getByText(/editOffer.submit/i));

        await waitFor(() => {
            expect(screen.getByText(/addOffer.errors.titleRequired/i)).toBeInTheDocument();
            expect(screen.getByText(/addOffer.errors.descriptionRequired/i)).toBeInTheDocument();
        });
    });
});
