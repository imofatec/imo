export default function SkeletonValidateCertificate() {
    return (
      <div className="h-screen flex flex-col items-center mt-[3.125rem] animate-pulse">
        <div className="w-1/2 flex flex-col items-center gap-10 pt-10">
          <div className="h-6 w-1/3 bg-gray-600 rounded" />
          <div className="h-4 w-2/3 bg-gray-700 rounded" />
          <div className="h-4 w-1/2 bg-gray-700 rounded" />
        </div>
  
        <div className="mt-10 w-1/2 flex flex-col items-center gap-4">
          <div className="h-4 w-full bg-gray-700 rounded" />
          <div className="h-10 w-full bg-gray-600 rounded" />
          <div className="h-10 w-1/3 bg-gray-500 rounded" />
        </div>
      </div>
    )
  }
  