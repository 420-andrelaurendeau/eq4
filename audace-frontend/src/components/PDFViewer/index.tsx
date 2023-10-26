import { Viewer } from '@react-pdf-viewer/core';
import { Worker } from '@react-pdf-viewer/core';
import '@react-pdf-viewer/core/lib/styles/index.css';

interface Props {
  pdf : string;
}

const PDFViewer = ({pdf} : Props) => {
  const base64toBlob = (data : string) => {
    const bytes = atob(data);
    let length = bytes.length;
    let out = new Uint8Array(length);
  
    while (length--) {
      out[length] = bytes.charCodeAt(length);
    }
  
    return new Blob([out], { type: 'application/pdf' });
  }

  const url = URL.createObjectURL(base64toBlob(pdf));

  return (
    <div>
      <Worker workerUrl="https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js">
        <Viewer fileUrl={url} />
      </Worker>
    </div>
  );
};

export default PDFViewer;