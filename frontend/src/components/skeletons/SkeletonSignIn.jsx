export default function SkeletonSignIn() {
    return (
      <>
        <div className="h-6 w-1/3 bg-gray-700 rounded mt-10 mb-10 mx-12 animate-pulse" />
        <div className="flex justify-center items-center bg-custom-dark-purple">
          <div className="h-screen mt-[3.125rem] text-white flex flex-col items-center">
            <div className="w-96 p-8 space-y-6 animate-pulse">
              <div className="flex flex-col items-center">
                <div className="h-6 w-24 bg-gray-700 rounded" />
              </div>
              <div className="space-y-2">
                <div className="h-4 w-20 bg-gray-600 rounded" />
                <div className="h-10 w-full bg-gray-700 rounded" />
              </div>
              <div className="space-y-2">
                <div className="h-4 w-20 bg-gray-600 rounded" />
                <div className="h-10 w-full bg-gray-700 rounded" />
              </div>
              <div className="h-10 w-full bg-gray-500 rounded" />
              <div className="h-4 w-1/2 bg-gray-700 rounded mx-auto" />
              <div className="flex items-center justify-center h-8 gap-2">
                <div className="h-[1px] w-12 bg-gray-600"></div>
                <div className="h-4 w-32 bg-gray-600 rounded" />
                <div className="h-[1px] w-12 bg-gray-600"></div>
              </div>
              <div className="flex justify-center gap-4">
                <div className="h-8 w-8 bg-gray-600 rounded-full" />
                <div className="h-8 w-8 bg-gray-600 rounded-full" />
              </div>
              <div className="flex justify-center mt-4">
                <div className="h-4 w-52 bg-gray-600 rounded" />
              </div>
            </div>
          </div>
        </div>
      </>
    )
  }
  