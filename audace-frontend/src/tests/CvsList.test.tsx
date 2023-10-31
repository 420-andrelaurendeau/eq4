import { render, screen } from '@testing-library/react';
import { UserType } from '../model/user';
import { CVStatus } from '../model/cv';
import CVsList from '../components/CVsList';

  jest.mock('axios', () => {
    return {
      create: jest.fn(() => ({
        get: jest.fn(),
        interceptors: {
          request: { use: jest.fn(), eject: jest.fn() },
          response: { use: jest.fn(), eject: jest.fn() }
        }
      }))
    }
  })
  const mockedUsedNavigate = jest.fn();

  jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom') as any,
    useNavigate: () => mockedUsedNavigate,
  }));

  jest.mock('../components/PDFViewer', () => ({
    return: {
        create: jest.fn(() => ({
            get: jest.fn(),
            interceptors: {
            request: { use: jest.fn(), eject: jest.fn() },
            response: { use: jest.fn(), eject: jest.fn() }
            }
        }))
    }
  }));

const cvs = [
    {
        id: 1,
        fileName: "Cv1",
        content: "pizza",
        student: {
            id: 1,
            firstName: "John",
            lastName: "Doe",
            email: "SFW@gmail.com",
            phone: "1234567890",
            address: "1234 Main St",
            password: "password",
            type: "Student",
            studentNumber: "1234567890"
        },
        cvStatus: CVStatus.PENDING
    }
]

it('should render the list of cvs', () => {
    render(<CVsList cvs={cvs} error={""} userType={UserType.Manager}/>);
    const linkElement = screen.getByText(/Cv1/i);
    expect(linkElement).not.toBeUndefined();
});
it('should have the accept and refuse buttons as manager', () => {
    render(<CVsList cvs={cvs} error={""} userType={UserType.Manager}/>);
    const acceptButton = screen.getByText(/Accept/i);
    const refuseButton = screen.getByText(/Refuse/i);
    expect(acceptButton).not.toBeUndefined();
    expect(refuseButton).not.toBeUndefined();
});