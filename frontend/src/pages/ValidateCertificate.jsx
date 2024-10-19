import { Titulo } from '@/components/ui/titulo'
import { useState } from 'react'
import CertificateForm from '@/components/ui/certificate/certificateForm'
import CertificateDetails from '@/components/ui/certificate/certificateDetails'
import useCertificateValidation from '@/hooks/useCertificateValidation'

export default function ValidateCertificate() {
  const { certificateData, error, fetchCertificateData } = useCertificateValidation()
  
  const handleGetCertificate = (certificateId) => {
    fetchCertificateData(certificateId)
  }
  return (
    <>
      <Titulo titulo={'Validar Certificado'}></Titulo>

      <div className="min-h-[50rem] flex flex-col items-center justify-center gap-10 ">

        <div className='h-80 w-1/2 flex flex-col items-center gap-10 pt-10 '>
          <h1 className='text-2xl'>Validar Certificado</h1>
          <p className='text-xl'>Deseja verificar se seu certificado é valido? </p>
          <p className='text-lg'>Informe o ID fornecido no fim de conclusão de curso</p>
        </div>

        <CertificateForm onValidate={handleGetCertificate} />
        {error && <p className="text-red-500">{error}</p>}
        {certificateData && <CertificateDetails data={certificateData} />}
      </div>
    </>
  )
}
