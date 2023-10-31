import { render, screen } from '@testing-library/react';
import FormInput from "../components/FormInput";

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
it('should take our label as a value', () => {
    render(<FormInput label={"test"} value={""} onChange={jest.fn()} errors={[]} formError={""} controlId={""}/>);
    const linkElement = screen.getByLabelText(/test/i);
    expect(linkElement).not.toBeUndefined();
});