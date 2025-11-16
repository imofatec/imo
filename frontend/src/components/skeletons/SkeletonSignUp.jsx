export default function SkeletonSignUp() {
    return (
      <>
        <div className="h-6 w-1/3 bg-gray-700 rounded mt-10 mb-10 mx-12 animate-pulse" />
        <div className="flex justify-center items-center bg-custom-dark-purple pb-8 animate-pulse">
          <div className="h-screen mt-[3.125rem] text-white">
            <div className="w-96 p-8 space-y-6">
              <div className="flex flex-col items-center">
                <div className="h-6 w-24 bg-gray-700 rounded" />
              </div>
              {Array.from({ length: 4 }).map((_, i) => (
                <div key={i} className="h-16 w-full bg-gray-700 rounded" />
              ))}
              <div className="h-10 w-full bg-gray-700 rounded" />
              <div className="h-6" />
              <div className="flex items-center justify-center h-8">
                <div className="h-[1px] w-12 bg-custom-border-gray" />
                <div className="h-4 w-40 bg-gray-700 rounded mx-4" />
                <div className="h-[1px] w-12 bg-custom-border-gray" />
              </div>
              <div className="flex justify-center gap-3">
                <div className="h-6 w-6 bg-gray-700 rounded-full" />
                <div className="h-6 w-6 bg-gray-700 rounded-full" />
              </div>
              <div className="flex justify-center">
                <div className="h-4 w-48 bg-gray-700 rounded" />
              </div>
            </div>
          </div>
        </div>
      </>
    )
  }
  