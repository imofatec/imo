import { Titulo } from '@/components/ui/titulo'
import { useEffect, useState } from 'react'
import CertificateForm from '@/components/ui/certificate/certificateForm'
import CertificateDetails from '@/components/ui/certificate/certificateDetails'
import useCertificateValidation from '@/hooks/useCertificateValidation'

export default function ValidateCertificate() {
  const [error, setError] = useState(null)
  const [certificateData, setCertificateData] = useState(null)
  const { fetchCertificateData } = useCertificateValidation()

  const handleGetCertificate = async (certificateId) => {
    const { error, success } = await fetchCertificateData(certificateId)
    if (error) {
      setError(error)
      return
    }
    setCertificateData(success)
  }

  return (
    <>
      <Titulo titulo={'IMO / Validar Certificado'}></Titulo>
      <div className="h-screen flex flex-col items-center mt-[3.125rem]">
        <div className="w-1/2 flex flex-col items-center gap-10 pt-10 ">
          <h1 className="text-xl font-bold">Validar Certificado</h1>

          {certificateData ? (
            <CertificateDetails data={certificateData} />
          ) : (
            <>
              <p>
                Informe o código do certificado para verificar sua integridade
              </p>
              <p className="text-red-500">{error && error}</p>
            </>
          )}
        </div>

        <CertificateForm
          onValidate={handleGetCertificate}
          setError={setError}
          setCertificateData={setCertificateData}
        />
      </div>
    </>
  )
}
