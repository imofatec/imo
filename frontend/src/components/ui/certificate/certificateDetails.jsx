export default function CertificateDetails({data}){
    return (
        <div>
            <h2>Dados do Certificado:</h2>
            <div className='pt-10'>
            <p>Nome completo: {data.username}</p>
            <p>Curso: {data.courseName}</p>
            <p>Iniciado em: {data.courseStartedAt}</p>
            <p>Finalizado em: {data.courseFinishedAt}</p>
            <p>Data de Emissão do certificado: {data.issuedAt}</p>
            </div>
          </div>
    )
}