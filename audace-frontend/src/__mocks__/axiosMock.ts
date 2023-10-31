const axiosMock: any = jest.createMockFromModule("axios");

axiosMock.create = jest.fn(() => axiosMock);
axiosMock.get = jest.fn(() => Promise.resolve({ data: {} }));
axiosMock.post = jest.fn(() => Promise.resolve({ data: {} }));
axiosMock.put = jest.fn(() => Promise.resolve({ data: {} }));
axiosMock.patch = jest.fn(() => Promise.resolve({ data: {} }));
axiosMock.delete = jest.fn(() => Promise.resolve({ data: {} }));
axiosMock.interceptors = {};
axiosMock.interceptors.request = {};
axiosMock.interceptors.request.use = jest.fn();
axiosMock.interceptors.response = {};
axiosMock.interceptors.response.use = jest.fn();

module.exports = axiosMock;

export {};
