import { render, screen } from '@testing-library/react';
import AppHeader from '../components/AppHeader';

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

it('should say Audace in the header', () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/Audace/i);
  expect(linkElement).not.toBeUndefined();
});
