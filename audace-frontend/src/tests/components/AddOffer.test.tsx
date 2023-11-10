import React from "react";
import { act, fireEvent, render, screen, waitFor } from "@testing-library/react";
import AddOffer from "../../components/AddOffer";
import '@testing-library/jest-dom';

jest.mock("react-i18next", () => ({
  useTranslation: () => ({
    t: (key: any) => key,
  }),
}));

const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));

jest.mock("../../services/authService", () => ({
  getUserId: jest.fn(),
}));

jest.mock("../../services/userService", () => ({
  getEmployerById: jest.fn(),
}));

jest.mock("../../services/departmentService", () => ({
  getAllDepartments: jest.fn(),
}));

jest.mock("../../services/offerService", () => ({
  employerCreateOffer: jest.fn(),
}));

const mockDepartments = [
  { id: 1, name: 'Department 1', code: 'D1' },
  { id: 2, name: 'Department 2', code: 'D2' },
];

const mockEmployer = {
  id: 1,
  name: 'Test Employer',
};

beforeEach(() => {
    jest.clearAllMocks();
  jest.resetAllMocks();
  const authService = require("../../services/authService");
  const userService = require("../../services/userService");
  const departmentService = require("../../services/departmentService");
  const offerService = require("../../services/offerService");

  authService.getUserId.mockReturnValue('1');
  userService.getEmployerById.mockResolvedValue({ data: mockEmployer });
  departmentService.getAllDepartments.mockResolvedValue({ data: mockDepartments });
  offerService.employerCreateOffer.mockResolvedValue({ status: 200 });
});

describe('AddOffer', () => {
  it('renders without crashing', async () => {
    render(<AddOffer />);
    expect(screen.getByText(/addOffer.pageTitle/i)).toBeInTheDocument();
  });

  it('loads departments on start', async () => {
    render(<AddOffer />);
    expect(await screen.findByText('Department 1')).toBeInTheDocument();
    expect(await screen.findByText('Department 2')).toBeInTheDocument();
  });

  it('submits an offer with valid data', async () => {
    render(<AddOffer />);

    await act(async () => {
      fireEvent.change(screen.getByLabelText(/addOffer.title/i), { target: { value: 'Test Title' } });
      fireEvent.change(screen.getByLabelText(/addOffer.description/i), { target: { value: 'Test Description' } });
      fireEvent.change(screen.getByLabelText(/addOffer.departmentCode/i), { target: { value: 'D1' } });
      fireEvent.change(screen.getByLabelText(/addOffer.startDate/i), { target: { value: '2021-01-01' } });
      fireEvent.change(screen.getByLabelText(/addOffer.endDate/i), { target: { value: '2021-06-01' } });
      fireEvent.change(screen.getByLabelText(/addOffer.offerEndDate/i), { target: { value: '2021-02-01' } });
      fireEvent.change(screen.getByLabelText(/addOffer.availablePlaces/i), { target: { value: '3' } });
      fireEvent.click(screen.getByText(/addOffer.submit/i));
    });

  });

  it('shows validation errors on submit with invalid data', async () => {
    render(<AddOffer />);

    fireEvent.click(screen.getByText(/addOffer.submit/i));

    await waitFor(() => {
      expect(screen.getByText(/addOffer.errors.titleRequired/i)).toBeInTheDocument();
      expect(screen.getByText(/addOffer.errors.descriptionRequired/i)).toBeInTheDocument();
    });
  });

  it('handles submit errors', async () => {
    const offerService = require("../../services/offerService");
    offerService.employerCreateOffer.mockRejectedValueOnce(new Error('Async error'));

    render(<AddOffer />);

    await act(async () => {
      fireEvent.change(screen.getByLabelText(/addOffer.title/i), { target: { value: 'Test Title' } });
      fireEvent.click(screen.getByText(/addOffer.submit/i));
    });

  });

  

});
